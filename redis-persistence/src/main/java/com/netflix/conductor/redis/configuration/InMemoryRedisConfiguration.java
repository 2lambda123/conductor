/*
 * Copyright 2020 Netflix, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.netflix.conductor.redis.configuration;

import com.netflix.conductor.redis.configuration.JedisCommandsConfigurer;
import com.netflix.conductor.redis.config.utils.RedisProperties;
import com.netflix.conductor.redis.jedis.JedisMock;
import com.netflix.conductor.redis.jedis.LocalHostSupplierProvider;
import com.netflix.dyno.connectionpool.HostSupplier;
import com.netflix.dyno.connectionpool.TokenMapSupplier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.commands.JedisCommands;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "db", havingValue = "memory", matchIfMissing = true)
public class InMemoryRedisConfiguration extends JedisCommandsConfigurer {

    @Bean
    public HostSupplier hostSupplier(RedisProperties properties) {
        return new LocalHostSupplierProvider(properties).get();
    }

    @Override
    protected JedisCommands createJedisCommands(RedisProperties properties, HostSupplier hostSupplier, TokenMapSupplier tokenMapSupplier) {
        return new JedisMock();
    }
}
