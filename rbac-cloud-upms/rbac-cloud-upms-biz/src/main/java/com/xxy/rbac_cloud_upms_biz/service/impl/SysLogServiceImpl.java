/*
 *  Copyright (c) 2019-2020, xxy (wangiegie@gmail.com).
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

package com.xxy.rbac_cloud_upms_biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxy.rbac.admin.api.entity.SysLog;
import com.xxy.rbac_cloud_upms_biz.mapper.SysLogMapper;
import com.xxy.rbac_cloud_upms_biz.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author xxy
 * @since 2020/4/28
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

}
