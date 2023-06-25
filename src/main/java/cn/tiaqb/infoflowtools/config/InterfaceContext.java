package cn.tiaqb.infoflowtools.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tianqingbo_dxm
 * @date 2023/6/25 11:29 AM
 * @since 1.0
 */
public class InterfaceContext {
    private static final ThreadLocal<InterfaceModel> THREAD_LOCAL = new ThreadLocal<>();

    public static void put(InterfaceModel interfaceModel) {
        THREAD_LOCAL.set(interfaceModel);
    }

    public static InterfaceModel get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    @Getter
    @Setter
    @ToString
    public static class InterfaceModel {
        private String router;
        private String header;
        private String method;
        private String remoteAddress;
        private String localAddress;
    }
}
