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

package com.xxy.rbac_cloud_auth.handler;

import com.xxy.common.security.handler.AbstractAuthenticationFailureEvenHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author xxy

 */
@Slf4j
@Component
public class RbacAuthenticationFailureEvenHandler extends AbstractAuthenticationFailureEvenHandler {

	/**
	 * 处理登录失败方法
	 * <p>
	 *
	 * @param authenticationException 登录的authentication 对象
	 * @param authentication          登录的authenticationException 对象
	 */
	@Override
	public void handle(AuthenticationException authenticationException, Authentication authentication) {
		log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());
	}
}
