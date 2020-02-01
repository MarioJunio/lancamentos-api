package br.com.mj.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	private Locale locale = LocaleContextHolder.getLocale();

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		List<ApiError> erros = Arrays.asList(createApiError("mensagem.invalida", ex));

		return super.handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		return handleExceptionInternal(ex, createValidationErrors(ex.getBindingResult()), headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		List<ApiError> errors = Arrays.asList(
				new ApiError(ExceptionUtils.getRootCauseMessage(ex), messageSource.getMessage("requisicao.operacao-nao-permitida", null, locale)));
		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ ServiceException.class })
	protected ResponseEntity<Object> handleServiceException(ServiceException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());

		return ResponseEntity.badRequest().body(new ApiError(mensagemUsuario, mensagemUsuario));
	}

	private List<ApiError> createValidationErrors(BindingResult br) {
		List<ApiError> errors = new ArrayList<>();

		for (FieldError fieldError : br.getFieldErrors()) {
			errors.add(new ApiError(fieldError.toString(), messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())));
		}

		return errors;
	}

	/**
	 * cria um erro para retornar na resposta da chamada ao endpoint
	 * 
	 * @param key
	 * @param e
	 * @return
	 */
	private ApiError createApiError(String key, Exception e) {
		return new ApiError(getDebugMessage(e), getClientMessage(key));
	}

	private String getClientMessage(String key) {
		return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
	}

	private String getDebugMessage(Exception e) {
		return e.getCause() != null ? e.getCause().toString() : e.toString();
	}
}
