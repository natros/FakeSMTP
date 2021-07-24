package com.nilhcem.fakesmtp.core.server;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.nilhcem.fakesmtp.core.exception.OutOfRangePortException;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;
import org.junit.jupiter.api.Test;

class SMTPServerHandlerTest {
  @Test
  void uniqueInstance() {
    SMTPServerHandler a = SMTPServerHandler.INSTANCE;
    SMTPServerHandler b = SMTPServerHandler.INSTANCE;
    assertSame(a, b);
  }

  @Test
  void testOutOfRangePort() {
    assertThrows(
        OutOfRangePortException.class, () -> SMTPServerHandler.INSTANCE.startServer(9999999, null));
  }

  @Test
  void stopShouldDoNothingIfServerIsAlreadyStopped() {
    SMTPServerHandler.INSTANCE.stopServer();
    SMTPServerHandler.INSTANCE.stopServer();
    SMTPServerHandler.INSTANCE.stopServer();
  }
}
