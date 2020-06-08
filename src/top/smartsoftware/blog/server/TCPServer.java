package top.smartsoftware.blog.server;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("服务器开启成功：端口号8888 ");
        while (true) {

            Socket socket = server.accept();
            System.out.println("有新客户端连接：" + socket.getRemoteSocketAddress());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = socket.getInputStream();
                        File file = new File("d:\\upload");
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        String fileName = System.currentTimeMillis() + (new Random(99999).nextInt() + ".png");
                        FileOutputStream fos = new FileOutputStream(file + "\\" + fileName);
                        int len = 0;
                        byte[] bytes = new byte[1024];
                        while ((len = is.read(bytes)) != -1) {
                            fos.write(bytes, 0, len);
                        }

                        socket.getOutputStream().write("上传成功".getBytes());
                        fos.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }


    }


}
