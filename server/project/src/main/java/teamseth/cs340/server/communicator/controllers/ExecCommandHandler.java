	package teamseth.cs340.server.communicator.controllers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import teamseth.cs340.server.util.Serializer;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;


/**
* @author Scott Leland Crossen
* @Copyright 2017 Scott Leland Crossen
*/
public class ExecCommandHandler implements HttpHandler {

	private static final Serializer serializer = Serializer.getInstance();

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		boolean success = false;

		try {
			if (exchange.getRequestMethod().toLowerCase().equals("post")) {

				Object reqCommand = serializer.getInstance().read(exchange.getRequestBody());
        Method method = reqCommand.getClass().getMethod("execute");
				Object invokedResult = method.invoke(reqCommand);
        Serializable result = (Serializable) invokedResult;

				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

				OutputStream respBody = exchange.getResponseBody();
				serializer.write(respBody, result);
				respBody.close();

				success = true;
			}

			if (!success) {
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				exchange.getResponseBody().close();
			}
		}
		catch (IOException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
			exchange.getResponseBody().close();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
			exchange.getResponseBody().close();
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
			exchange.getResponseBody().close();
			e.printStackTrace();
    } catch (IllegalAccessException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
			exchange.getResponseBody().close();
			e.printStackTrace();
    } catch (InvocationTargetException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
			exchange.getResponseBody().close();
			e.printStackTrace();
    }
	}

	private String readString(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStreamReader sr = new InputStreamReader(is);
		char[] buf = new char[1024];
		int len;
		while ((len = sr.read(buf)) > 0) {
			sb.append(buf, 0, len);
		}
		return sb.toString();
	}

	private void writeInt(Integer num, OutputStream os) throws IOException {
		OutputStreamWriter sw = new OutputStreamWriter(os);
		sw.write(Integer.toString(num));
		sw.flush();
	}
}
