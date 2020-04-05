package com.xcm.controller.admin;

import com.xcm.directive.FlashMessageDirective;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * controller - 基类
 */
public class BaseController {

    /**
     * 添加瞬时消息
     *
     * @param redirectAttributes RedirectAttributes
     * @param message            消息
     */
    protected void addFlashMessage(RedirectAttributes redirectAttributes, String message) {
        if (redirectAttributes != null && StringUtils.isNotBlank(message)) {
            redirectAttributes.addFlashAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
        }
    }
}
