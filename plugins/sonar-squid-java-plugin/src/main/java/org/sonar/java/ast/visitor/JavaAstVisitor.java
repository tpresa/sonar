/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2008-2012 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.java.ast.visitor;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import org.sonar.api.resources.InputFile;
import org.sonar.squid.api.CodeVisitor;
import org.sonar.squid.api.SourceClass;
import org.sonar.squid.api.SourceCode;
import org.sonar.squid.api.SourcePackage;
import org.sonar.squid.text.Source;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public abstract class JavaAstVisitor implements CodeVisitor {

  private Stack<SourceCode> sourceCodeStack;

  private FileContents fileContents;

  private InputFile inputFile;

  private Source source;

  public final void setFileContents(FileContents fileContents) {
    this.fileContents = fileContents;
  }

  public final FileContents getFileContents() {
    return fileContents;
  }

  public final void setSource(Source source) {
    this.source = source;
  }

  public final InputFile getInputFile() {
    return inputFile;
  }

  public final void setInputFile(InputFile inputFile) {
    this.inputFile = inputFile;
  }

  final Source getSource() {
    return source;
  }

  public List<Integer> getWantedTokens() {
    return Collections.emptyList();
  }

  public final void setSourceCodeStack(Stack<SourceCode> sourceCodeStack) {
    this.sourceCodeStack = sourceCodeStack;
  }

  public final void addSourceCode(SourceCode child) {
    peekSourceCode().addChild(child);
    sourceCodeStack.add(child);
  }

  public final void popSourceCode() {
    sourceCodeStack.pop();
  }

  public final SourceCode peekSourceCode() {
    return sourceCodeStack.peek();
  }

  public final SourcePackage peekParentPackage() {
    if (peekSourceCode().getClass().equals(SourcePackage.class)) {
      return (SourcePackage) peekSourceCode();
    }
    return peekSourceCode().getParent(SourcePackage.class);
  }

  public final SourceClass peekParentClass() {
    if (peekSourceCode().getClass().equals(SourceClass.class)) {
      return (SourceClass) peekSourceCode();
    }
    return peekSourceCode().getParent(SourceClass.class);
  }

  public void visitFile(DetailAST ast) {
  }

  public void visitToken(DetailAST ast) {
  }

  public void leaveToken(DetailAST ast) {
  }

  public void leaveFile(DetailAST ast) {
  }
}
