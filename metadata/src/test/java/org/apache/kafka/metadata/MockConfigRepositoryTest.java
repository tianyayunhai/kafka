/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.metadata;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MockConfigRepositoryTest {

    @Test
    public void testEmptyRepository() {
        MockConfigRepository repository = new MockConfigRepository();
        assertEquals(new Properties(), repository.brokerConfig(0));
        assertEquals(new Properties(), repository.topicConfig("foo"));
        assertEquals(new Properties(), repository.groupConfig("group"));
    }

    @Test
    public void testSetTopicConfig() {
        MockConfigRepository repository = new MockConfigRepository();
        String topic0 = "topic0";
        repository.setTopicConfig(topic0, "foo", null);

        String topic1 = "topic1";
        repository.setTopicConfig(topic1, "foo", "bar");
        Properties topicProperties = new Properties();
        topicProperties.put("foo", "bar");
        assertEquals(topicProperties, repository.topicConfig(topic1));

        Properties topicProperties2 = new Properties();
        topicProperties2.put("foo", "bar");
        topicProperties2.put("foo2", "baz");
        repository.setTopicConfig(topic1, "foo2", "baz"); // add another prop
        assertEquals(topicProperties2, repository.topicConfig(topic1)); // should get both props

        repository.setTopicConfig(topic1, "foo2", null);
        assertEquals(topicProperties, repository.topicConfig(topic1));
    }
}
