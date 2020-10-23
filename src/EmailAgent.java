import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class EmailAgent {

    public static void main(String[] args) throws Exception{

        // write your code here

        // Establish a TCP connection with the mail server.
        // SMTP port 25 continues to be used primarily for SMTP relaying. SMTP relaying is the transmission of email from email server to email server.
        Socket socket = new Socket("localhost", 25 );
        // Create a BufferedReader to read a line at a time.
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        // Read greeting from the server.
        String response = br.readLine();
        System.out.println(response);
        if (!response.startsWith("220")) {
            throw new Exception("220 reply not received from server.");
        }

        // Get a reference to the socket's output stream.

        OutputStream os = socket.getOutputStream();
        // Send HELO command and get server response.
        String command = "HELO MELISSA\r\n";
        System.out.print(command);
        os.write(command.getBytes("US-ASCII"));
        response = br.readLine();
        System.out.println(response);
        if (!response.startsWith("250")) throw new Exception("250 reply not received from server.");


        // Send MAIL FROM command.

        String mailFrom = "MAIL FROM: mn1030@mynsu.nova.edu \r\n";
        System.out.print(mailFrom);
        os.write(mailFrom.getBytes("US-ASCII"));
        response = br.readLine();
        System.out.println(response);
        if (!response.startsWith("250")) {
            throw new Exception("250 reply not received from server.");
        }



        // Send RCPT TO command.

        String commandRTPT = "RCPT TO: mn1030@mynsu.nova.edu \r\n";
        System.out.print(commandRTPT);
        os.write(commandRTPT.getBytes("US-ASCII"));
        response = br.readLine();
        System.out.println(response);
        if (!response.startsWith("250")) {
            throw new Exception("250 reply not received from server.");
        }


        // Send DATA command.
        String commandDATA = "DATA TO: mn1030@mynsu.nova.edu\r\n";
        System.out.print(commandDATA);
        os.write(commandDATA.getBytes("US-ASCII"));
        response = br.readLine();
        System.out.println(response);
        if (!response.startsWith("354")) {
            socket.close();
            throw new Exception("354 reply not received from server.");
        }

        // Send message data.
        String sendMessage = "email sent";
        System.out.print(sendMessage);
        os.write(sendMessage.getBytes("US-ASCII"));
        os.write("email sent".getBytes("US-ASCII"));
        os.write("SUBJECT: test message\r\n\r\n".getBytes("US-ASCII"));
        os.write("hello world, My name is Melissa\r\n".getBytes("US-ASCII"));




        // End with line with a single period.

        String singlePeriod = ".\r\n";
        System.out.print(singlePeriod);
        os.write(singlePeriod.getBytes("US-ASCII"));
        response = br.readLine();
        System.out.println(response);
        if (!response.startsWith("250")) {
            socket.close();
            throw new Exception("250 reply not received from server.\r\n\r\n");
        }



        // Send QUIT command.
        // closing connection <SERVER> Service closing transmission channel
        String commandQUIT = "QUIT";
        System.out.print(commandQUIT);
        os.write(commandQUIT.getBytes("US-ASCII"));
        response = br.readLine();
        System.out.println(response);
        if (!response.startsWith("221")) throw new Exception("221 reply not received from server.\r\n\r\n");

        socket.close();



    }



}


//resources: https://www.samlogic.net/articles/smtp-commands-reference.htm
//https://www.geeksforgeeks.org/send-email-using-java-program/
//https://serverfault.com/questions/509464/error-message-not-sent-server-replied-354/509469#509469