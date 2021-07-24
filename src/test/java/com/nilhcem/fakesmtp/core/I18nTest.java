package com.nilhcem.fakesmtp.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class I18nTest {
  private static Locale defaultLocale;

  @BeforeAll
  static void initLocale() {
    defaultLocale = Locale.getDefault();
    Locale.setDefault(Locale.TAIWAN);
  }

  @AfterAll
  static void resetLocale() {
    Locale.setDefault(defaultLocale);
  }

  @Test
  void uniqueInstance() {
    I18n a = I18n.INSTANCE;
    I18n b = I18n.INSTANCE;
    assertSame(a, b);
  }

  @Test
  void getEmptyValueWhenKeyIsNotFound() {
    assertTrue(I18n.INSTANCE.get("this.key.doesnt.exist").isEmpty());
  }

  @Test
  void getValueWhenKeyIsFound() {
    assertFalse(I18n.INSTANCE.get("menubar.file").isEmpty());
  }
}
