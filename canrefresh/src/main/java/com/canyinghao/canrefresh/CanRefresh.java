package com.canyinghao.canrefresh;

/**
 * Copyright 2016 canyinghao
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public interface CanRefresh {


    /**
     * 重置
     */
    public void onReset();


    /**
     * 下拉高度大于头部高度
     */
    public void onPrepare();


    /**
     * 放手后
     */
    public void onRelease();

    /**
     * 刷新完成
     */
    public void onComplete();

    /**
     * 下拉高度与头部高度比例
     */
    public void onPositionChange(float currentPercent);


    /**
     * 是下拉还是上拉
     * @param isHeader boolean
     */
    public void setIsHeaderOrFooter(boolean isHeader);
}
