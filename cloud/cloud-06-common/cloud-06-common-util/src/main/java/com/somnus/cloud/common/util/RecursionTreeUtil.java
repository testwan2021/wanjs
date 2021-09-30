/*
 * Copyright 2002-2018 the original author or authors.
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
package com.somnus.cloud.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: RecursionTreeUtil
 * @Description: The class Recursion tree util.
 * @author Somnus
 * @date 2018年10月9日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecursionTreeUtil {
	/**
	 * Gets child tree nodes.
	 *
	 * @param list     the list
	 * @param parentId the parent id
	 *
	 * @return the child tree nodes
	 */
	public static List<TreeNode> getChildTreeNodes(List<TreeNode> list, Long parentId) {
		List<TreeNode> returnList = new ArrayList<>();

		for (TreeNode treeNode : list) {
			if (treeNode.getPid() == null) {
				continue;
			}

			if (Objects.equals(treeNode.getPid(), parentId)) {
				recursionFn(list, treeNode);
				returnList.add(treeNode);
			}
		}
		return returnList;
	}

	/**
	 * 递归列表
	 */
	private static void recursionFn(List<TreeNode> list, TreeNode node) {
		List<TreeNode> childList = getChildList(list, node);
		if (PublicUtil.isEmpty(childList)) {
			return;
		}
		node.setChildren(childList);
		for (TreeNode tChild : childList) {
			recursionFn(list, tChild);
		}
	}

	/**
	 * 得到子节点列表
	 */
	private static List<TreeNode> getChildList(List<TreeNode> list, TreeNode t) {
		List<TreeNode> tList = new ArrayList<>();

		for (TreeNode treeNode : list) {
			if (PublicUtil.isEmpty(treeNode.getPid())) {
				continue;
			}
			if (Objects.equals(treeNode.getPid(), t.getId())) {
				tList.add(treeNode);
			}
		}
		return tList;
	}
}
