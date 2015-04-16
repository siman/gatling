/**
 * Copyright 2011-2014 eBusiness Information, Groupe Excilys (www.ebusinessinformation.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gatling.http.action

import akka.actor.ActorContext
import io.gatling.core.session.Session
import io.gatling.core.action.interceptor.ActionInterceptor
import io.gatling.http.Predef.Request
import io.gatling.http.ahc.{HttpEngine, HttpTx}

class HttpRequestActionInterceptor(session: Session) extends ActionInterceptor[HttpRequestAction](session) {

  def onSendHttpRequest(requestName: String, httpEngine: HttpEngine, tx: HttpTx)(implicit ctx: ActorContext): Unit = {
    httpEngine.startHttpTransaction(tx)
  }
}

/** Debug by logging an info about HTTP request and session vars. */
abstract class DebugHttpRequestActionInterceptor(session: Session) extends HttpRequestActionInterceptor(session) {

  override def onSendHttpRequest(requestName: String, httpEngine: HttpEngine, tx: HttpTx)(implicit ctx: ActorContext): Unit = {
    debugRequest(requestName, tx.request.ahcRequest)
  }

  def debugRequest(requestName: String, request: Request): Unit
}
