/*
 * Copyright 2011 Andreas Veithen
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
package com.googlecode.sisme;

public abstract class MessageContext {
    private final Exchange exchange;
    private final MessageData messageData;

    public MessageContext(Exchange exchange, MessageData messageData) {
        this.exchange = exchange;
        this.messageData = messageData;
    }

    public final Exchange getExchange() {
        return exchange;
    }

    public final MessageData getMessageData() {
        return messageData;
    }
}
