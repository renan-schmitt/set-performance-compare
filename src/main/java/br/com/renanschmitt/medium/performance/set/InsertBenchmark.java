/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package br.com.renanschmitt.medium.performance.set;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
public class InsertBenchmark {

  @Param({"1", "10", "100", "1000", "10000", "100000", "1000000"})
  public int size;

  @Param({"true", "false"})
  public boolean ordered;

  private Integer[] numbers;
  private String[] texts;

  @Setup
  public void setUp() {
    numbers = new Integer[size];
    texts = new String[size];

    for (int i = 0; i < size; i++) {
      numbers[i] = i;
      texts[i] = RandomStringUtils.randomAlphabetic(30);
    }

    if (!ordered) {
      var tempList = new ArrayList<>(List.of(numbers));
      Collections.shuffle(tempList);
      numbers = tempList.toArray(Integer[]::new);

      var tempListStr = new ArrayList<>(List.of(texts));
      Collections.shuffle(tempListStr);
      texts = tempListStr.toArray(String[]::new);
    }
  }

  @Benchmark
  public void testTreeSet() {
    var set = new TreeSet<BigDecimal>();

    for (var num : numbers) {
      set.add(BigDecimal.valueOf(num));
    }
  }

  @Benchmark
  public void testTreeSetString() {
    var set = new TreeSet<String>();

    for (var str : texts) {
      set.add(str);
    }
  }

  @Benchmark
  public void testHashSet() {
    var set = new HashSet<BigDecimal>();

    for (var num : numbers) {
      set.add(BigDecimal.valueOf(num));
    }
  }

  @Benchmark
  public void testHashSetString() {
    var set = new HashSet<String>();

    for (var str : texts) {
      set.add(str);
    }
  }

  @Benchmark
  public void testLinkedHashSet() {
    var set = new LinkedHashSet<BigDecimal>();

    for (var num : numbers) {
      set.add(BigDecimal.valueOf(num));
    }
  }

  @Benchmark
  public void testLinkedHashSetString() {
    var set = new LinkedHashSet<String>();

    for (var str : texts) {
      set.add(str);
    }
  }
}
