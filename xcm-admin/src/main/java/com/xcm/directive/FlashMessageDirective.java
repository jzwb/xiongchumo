package com.xcm.directive;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Directive - 瞬时消息
 */
@Component("flashMessageDirective")
public class FlashMessageDirective implements TemplateDirectiveModel {

	/**
	 * "瞬时消息"属性名称
	 */
	public static final String FLASH_MESSAGE_ATTRIBUTE_NAME = FlashMessageDirective.class.getName() + ".FLASH_MESSAGE";

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "flashMessage";

	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws IOException {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		String message = (String) requestAttributes.getAttribute(FLASH_MESSAGE_ATTRIBUTE_NAME, RequestAttributes.SCOPE_REQUEST);
		if (body != null) {
			//setLocalVariable(VARIABLE_NAME, message, env, body);
		} else {
			if (StringUtils.isNotBlank(message)) {
				Writer out = env.getOut();
				out.write("layer.msg('" + message + "', {time: 2000});");
			}
		}
	}
}