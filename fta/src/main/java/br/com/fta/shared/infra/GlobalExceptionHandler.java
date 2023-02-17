package br.com.fta.shared.infra;

import br.com.fta.shared.exceptions.ServiceUnavailableException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalExceptionHandler {
	public static final String DEFAULT_ERROR_VIEW = "error";

	@ExceptionHandler(ServiceUnavailableException.class)
	public ModelAndView serviceUnavailableExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;

		ModelAndView mav = new ModelAndView();
		mav.addObject("error", e.getMessage());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		mav.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
		return mav;
	}

	@ExceptionHandler({RuntimeException.class})
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;

		ModelAndView mav = new ModelAndView();
		mav.addObject("error", "An unexpected error has occurred.");
		mav.setViewName(DEFAULT_ERROR_VIEW);
		mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		return mav;
	}
}