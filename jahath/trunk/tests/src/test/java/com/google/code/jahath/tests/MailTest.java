/*
 * Copyright 2009 Andreas Veithen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.code.jahath.tests;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.junit.Assert;
import org.junit.Test;

import com.google.code.jahath.client.JahathClient;
import com.google.code.jahath.client.ProxyConfiguration;
import com.google.code.jahath.client.Tunnel;
import com.google.code.jahath.server.JahathServer2;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

public class MailTest {
    private final int smtpPort = 9001;
    private final int imapPort = 9002;
    private final int serverPort = 9003;
    private final int smtpTunnelPort = 9004;
    private final int imapTunnelPort = 9005;
    private final int proxyPort = 9006;
    
    @Test public void test() {}
    
/*    
    private void test(boolean useProxy) throws Exception {
        GreenMail greenMail = new GreenMail(new ServerSetup[] {
                new ServerSetup(smtpPort, "127.0.0.1", ServerSetup.PROTOCOL_SMTP),
                new ServerSetup(imapPort, "127.0.0.1", ServerSetup.PROTOCOL_IMAP) });
        greenMail.setUser("user@localhost", "user", "password");
        greenMail.start();
        
        JahathServer2 server = new JahathServer2(serverPort);
        
        ProxyServer proxy;
        ProxyConfiguration proxyConfiguration;
        if (useProxy) {
            proxy = new ProxyServer(proxyPort);
            proxyConfiguration = new ProxyConfiguration("localhost", proxyPort);
        } else {
            proxy = null;
            proxyConfiguration = null;
        }
        
        JahathClient client = new JahathClient("localhost", serverPort, proxyConfiguration);
        Tunnel smtpTunnel = client.createTunnel(smtpTunnelPort, "localhost", smtpPort);
        Tunnel imapTunnel = client.createTunnel(imapTunnelPort, "localhost", imapPort);
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", String.valueOf(smtpTunnelPort));
        props.put("mail.imap.host", "localhost");
        props.put("mail.imap.port", String.valueOf(imapTunnelPort));
        props.put("mail.imap.user", "user");
        props.put("mail.imap.password", "password");
        Session session = Session.getInstance(props);
        
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("user@localhost"));
        msg.setRecipient(RecipientType.TO, new InternetAddress("user@localhost"));
        msg.setSubject("test");
        msg.setContent("This is a test message", "text/plain");
        Transport.send(msg);
        
        Store store = session.getStore("imap");
        store.connect("user", "password");
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        Message[] messages = folder.getMessages();
        Assert.assertEquals(1, messages.length);
        msg = (MimeMessage)messages[0];
        Assert.assertEquals("test", msg.getSubject());
        folder.close(false);
        store.close();
        
        smtpTunnel.stop();
        imapTunnel.stop();
        client.shutdown();
        
        if (useProxy) {
            Assert.assertEquals(2, proxy.getRequestCount());
            proxy.stop();
        }
        
        server.stop();
        
        greenMail.stop();
    }

    @Test
    public void testWithoutProxy() throws Exception {
        test(false);
    }

//    @Test
    public void testWithProxy() throws Exception {
        test(true);
    }
*/
}