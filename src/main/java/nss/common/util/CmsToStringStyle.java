/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package nss.common.util;

import org.apache.commons.lang3.builder.ToStringStyle;

public class CmsToStringStyle extends ToStringStyle {

    private static final long serialVersionUID = 8209779405497973286L;

    public static final ToStringStyle URL_PARAMETER_STYLE = new UrlParamterToStringStyle();

    private static final class UrlParamterToStringStyle extends ToStringStyle {

        private static final long serialVersionUID = 1L;

        /**
         * Constructor.
         * Use the static constant rather than instantiating.
         */
        UrlParamterToStringStyle() {
            super();
            this.setUseClassName(false);
            this.setUseIdentityHashCode(false);
            this.setContentStart("");
            this.setContentEnd("");
            this.setFieldSeparator("abc@=-=@def");
            this.setNullText("");
        }

        /**
         * Ensure Singleton after serialization.
         * @return the singleton
         */
        private Object readResolve() {
            return CmsToStringStyle.URL_PARAMETER_STYLE;
        }

    }
}

