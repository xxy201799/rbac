/*
 *  Copyright (c) 2020-2020, xxy (wangiegie@gmail.com).
 *  <p>
 *  Licensed under the GNU Lesser General Public License 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 * https://www.gnu.org/licenses/lgpl.html
 *  <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xxy.common.security.feign;

import feign.RequestInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.security.oauth2.client.AccessTokenContextRelay;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

/**
 * @author xxy

 * feign 拦截器传递 header 中oauth token，
 * 使用hystrix 的信号量模式
 */
@Configuration
@AllArgsConstructor
@ConditionalOnProperty("security.oauth2.client.client-id")
public class RbacFeignClientConfiguration {
	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext,
															OAuth2ProtectedResourceDetails resource,
															AccessTokenContextRelay accessTokenContextRelay) {
		return new RbacFeignClientInterceptor(oAuth2ClientContext, resource, accessTokenContextRelay);
	}
}
