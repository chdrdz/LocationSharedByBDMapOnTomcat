package com.random.sharedlocation.servlet;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * 设置servlet监听，随着tomcat启动而自定加载
 */
public class SocketServlet extends HttpServlet implements
		ServletContextListener {

	/**
	 * 设置端口号
	 */
	private static final int PORT = 12345;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Servlet加载完毕
	 */
	public void contextInitialized(ServletContextEvent sce) {

		try {
			// 1.服务器获取套接字连接
			NioSocketAcceptor acceptor = new NioSocketAcceptor();
			acceptor.getFilterChain().addLast("Line",
					new ProtocolCodecFilter(new TextLineCodecFactory()));

			// 2.套接字链接建立后，接收数据与发送数据
			acceptor.setHandler(new SocketHandler());

			// 3.绑定端口号
			acceptor.bind(new InetSocketAddress(PORT));
			System.out.println("Server started at port by Tomcat " + PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Servlet销毁
	 */
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
