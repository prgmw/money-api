package br.com.money.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MoneyExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error(ex.getCause().getMessage());
		return super.handleExceptionInternal(ex, new ErroMessage("Error", ex.getCause().getMessage()), headers,
				HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ErroMessage> createErroList = createErroList(ex.getBindingResult());
		return super.handleExceptionInternal(ex, createErroList, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {
		return super.handleExceptionInternal(ex, new ErroMessage("Error", ex.getMessage()), new HttpHeaders(),
				HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {

		System.getProperties();

		String msgDev = ExceptionUtils.getRootCauseMessage(ex);
//		return super.handleExceptionInternal(ex,
//				new ErroMessage(messageSource.getMessage("recurso.operacao.nao.permitida", null, getLocale()), msgDev),
//				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		
		return super.handleExceptionInternal(ex,
				new ErroMessage("Error", msgDev),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private Locale getLocale() {
		return new Locale("pt", "BR");
	}

	//

	private List<ErroMessage> createErroList(BindingResult binding) {
		List<ErroMessage> erros = new ArrayList<ErroMessage>();
		for (FieldError error : binding.getFieldErrors()) {
			erros.add(new ErroMessage(messageSource.getMessage(error, LocaleContextHolder.getLocale()),
					error.toString()));
		}
		return erros;
	}

}
