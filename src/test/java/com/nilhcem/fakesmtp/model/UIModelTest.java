package com.nilhcem.fakesmtp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nilhcem.fakesmtp.core.test.TestConfig;
import org.junit.jupiter.api.Test;

class UIModelTest {
  @Test
  void uniqueInstance() {
    UIModel a = UIModel.INSTANCE;
    UIModel b = UIModel.INSTANCE;
    assertSame(a, b);
  }

  @Test
  void shouldHaveZeroMsgReceivedFirst() {
    assertEquals(0, UIModel.INSTANCE.getNbMessageReceived());
  }

  @Test
  void testInvalidPort() {
    UIModel.INSTANCE.setPort("INVALID");
    assertThrows(Exception.class, UIModel.INSTANCE::toggleButton);
  }

  @Test
  void testInvalidHost() {
    UIModel.INSTANCE.setHost("INVALID");
    assertThrows(Exception.class, UIModel.INSTANCE::toggleButton);
  }

  @Test
  void testIsStarted() throws Exception {
    UIModel.INSTANCE.setPort(Integer.toString(TestConfig.PORT_UNIT_TESTS));
    assertFalse(UIModel.INSTANCE.isStarted());

    UIModel.INSTANCE.toggleButton();
    assertTrue(UIModel.INSTANCE.isStarted());

    UIModel.INSTANCE.toggleButton();
    assertFalse(UIModel.INSTANCE.isStarted());
  }
}
