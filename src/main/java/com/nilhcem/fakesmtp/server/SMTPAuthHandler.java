package com.nilhcem.fakesmtp.server;

import org.jetbrains.annotations.Nullable;
import org.subethamail.smtp.AuthenticationHandler;

/**
 * Simulates an authentication handler to allow capturing emails that are set up with login
 * authentication.
 *
 * @author jasonpenny
 * @since 1.2
 */
/*package*/ final class SMTPAuthHandler implements AuthenticationHandler {
  private static final String USER_IDENTITY = "User";
  private static final String PROMPT_USERNAME =
      "334 VXNlcm5hbWU6"; // VXNlcm5hbWU6 is base64 for "Username:"
  private static final String PROMPT_PASSWORD =
      "334 UGFzc3dvcmQ6"; // UGFzc3dvcmQ6 is base64 for "Password:"
  private int pass;

  /**
   * Simulates an authentication process.
   *
   * <p>
   *
   * <ul>
   *   <li>first prompts for username;
   *   <li>then, prompts for password;
   *   <li>finally, returns {@code null} to finish the authentication process;
   * </ul>
   *
   * @param clientInput The client's input, eg "AUTH PLAIN dGVzdAB0ZXN0ADEyMzQ="
   * @return <code>null</code> if the authentication process is finished, otherwise a string to hand
   *     back to the client.
   */
  @Override
  public @Nullable String auth(String clientInput) {
    String prompt;

    if (++pass == 1) {
      prompt = PROMPT_USERNAME;
    } else if (pass == 2) {
      prompt = PROMPT_PASSWORD;
    } else {
      pass = 0;
      prompt = null;
    }
    return prompt;
  }

  /**
   * If the authentication process was successful, this returns the identity of the user. The type
   * defining the identity can vary depending on the authentication mechanism used, but typically
   * this returns a String username. If authentication was not successful, the return value is
   * undefined.
   */
  @Override
  public Object getIdentity() {
    return USER_IDENTITY;
  }
}
