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

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
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
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 10, time = 1)
public class LoopBenchmark {

  @Param({
    "1", "10", "100", "500", "1000", "5000", "10000", "50000", "100000", "250000", "500000",
    "750000", "1000000"
  })
  public int size;

  private Set<BigInteger> treeSet;
  private Set<BigInteger> hashSet;
  private Set<BigInteger> linkedHashSet;

  @Setup
  public void setUp() {
    var numbers = new Integer[size];

    for (int i = 0; i < size; i++) {
      numbers[i] = i;
    }

    treeSet = new TreeSet<>(Stream.of(numbers).map(BigInteger::valueOf).toList());
    hashSet = new HashSet<>(Stream.of(numbers).map(BigInteger::valueOf).toList());
    linkedHashSet = new HashSet<>(Stream.of(numbers).map(BigInteger::valueOf).toList());
  }

  @Benchmark
  public void testTreeSet() {
    var result = new AtomicReference<>(BigInteger.ZERO);
    treeSet.stream().sorted().forEach(num -> result.set(result.get().add(num)));
  }

  @Benchmark
  public void testHashSet() {
    var result = new AtomicReference<>(BigInteger.ZERO);
    hashSet.stream().sorted().forEach(num -> result.set(result.get().add(num)));
  }

  @Benchmark
  public void testLinkedHashSet() {
    var result = new AtomicReference<>(BigInteger.ZERO);
    linkedHashSet.stream().sorted().forEach(num -> result.set(result.get().add(num)));
  }
}
