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
 * ����servlet����������tomcat�������Զ�����
 */
public class SocketServlet extends HttpServlet implements
		ServletContextListener {

	/**
	 * ���ö˿ں�
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
	 * Servlet�������
	 */
	public void contextInitialized(ServletContextEvent sce) {

		try {
			// 1.��������ȡ�׽�������
			NioSocketAcceptor acceptor = new NioSocketAcceptor();
			acceptor.getFilterChain().addLast("Line",
					new ProtocolCodecFilter(new TextLineCodecFactory()));

			// 2.�׽������ӽ����󣬽��������뷢������
			acceptor.setHandler(new SocketHandler());

			// 3.�󶨶˿ں�
			acceptor.bind(new InetSocketAddress(PORT));
			System.out.println("Server started at port by Tomcat " + PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Servlet����
	 */
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
