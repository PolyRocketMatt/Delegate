package com.github.polyrocketmatt.delegate.impl;

import com.velocitypowered.api.proxy.ProxyServer;

public class Delegate {

    private static ProxyServer proxyServer;

    public static ProxyServer getProxyServer() {
        return proxyServer;
    }
}
