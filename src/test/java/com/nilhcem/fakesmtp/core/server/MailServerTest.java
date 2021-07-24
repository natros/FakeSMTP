package com.nilhcem.fakesmtp.core.server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nilhcem.fakesmtp.model.EmailModel;
import com.nilhcem.fakesmtp.model.UIModel;
import com.nilhcem.fakesmtp.server.MailSaver;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Observable;
import java.util.Observer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MailServerTest {
  private static MailSaver saver;

  @BeforeAll
  static void createMailSaver() {
    saver = new MailSaver();
  }

  @Test
  void testGetLock() {
    assertSame(saver, saver.getLock());
  }

  @Test
  void testSaveDeleteEmail() {
    final String from = "from@example.com";
    final String to = "to@example.com";
    final String subject = "Hello";
    final String content = "How are you?";

    // Save
    InputStream data = fromString(getMockEmail(from, to, subject, content));
    Observer mockObserver =
        new Observer() {
          @Override
          public void update(Observable o, Object arg) {
            EmailModel model = (EmailModel) arg;

            assertEquals(from, model.getFrom());
            assertEquals(to, model.getTo());
            assertEquals(subject, model.getSubject());
            assertEquals(to, model.getTo());
            assertNotNull(model.getEmailStr());
            assertFalse(model.getEmailStr().isEmpty());
            assertNotNull(model.getFilePath());
            assertFalse(model.getFilePath().isEmpty());

            File file = new File(model.getFilePath());
            assertTrue(file.exists());

            // Delete
            UIModel.INSTANCE.getListMailsMap().put(0, model.getFilePath());
            saver.deleteEmails();
            assertFalse(file.exists());
          }
        };
    saver.addObserver(mockObserver);
    assertThat(saver.countObservers()).isNotZero();
    saver.saveEmailAndNotify(from, to, data);
    saver.deleteObserver(mockObserver);
  }

  private static InputStream fromString(String str) {
    byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
    return new ByteArrayInputStream(bytes);
  }

  private static String getMockEmail(String from, String to, String subject, String content) {
    String br = System.getProperty("line.separator");

    String sb =
        "Line 1 will be removed"
            + br
            + "Line 2 will be removed"
            + br
            + "Line 3 will be removed"
            + br
            + "Line 4 will be removed"
            + br
            + "Date: Thu, 15 May 2042 04:42:42 +0800 (CST)"
            + br
            + String.format("From: \"%s\" <%s>%n", from, from)
            + String.format("To: \"%s\" <%s>%n", to, to)
            + "Message-ID: <17000042.0.1300000000042.JavaMail.wtf@OMG00042>"
            + br
            + String.format("Subject: %s%n", subject)
            + "MIME-Version: 1.0"
            + br
            + "Content-Type: text/plain; charset=us-ascii"
            + br
            + "Content-Transfer-Encoding: 7bit"
            + br
            + br
            + content
            + br
            + br;
    return sb;
  }
}
