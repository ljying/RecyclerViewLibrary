/*
 * Copyright 2016 Yan Zhenjie
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
package ljying.github.io.swiperecyclerview.touch;

/**
 * Description: 条目移动监听
 *
 * @author Li Jianying
 * @version 2.0
 * @since 2016/4/19
 */
public interface OnItemMoveListener {

    /**
     * 拖拽时回调
     *
     * @param fromPosition 起始位置
     * @param toPosition   结束位置
     * @return 若自己处理返回true，不处理返回false
     */
    boolean onItemMove(int fromPosition, int toPosition);

    /**
     * 条目滑动时
     *
     * @param adapterPosition   侧滑位置.
     * @param direction 滑动方向
     */
    void onItemSwipe(int adapterPosition, int direction);

}
