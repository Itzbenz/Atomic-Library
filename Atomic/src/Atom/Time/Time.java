/*
 * Copyright 2021 Itzbenz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package Atom.Time;

import Atom.Utility.Meth;
import Atom.Utility.Utility;

import java.util.concurrent.TimeUnit;

public class Time {
	final TimeUnit tu;
	final long src;
	
	public Time() {
		this(TimeUnit.NANOSECONDS, System.nanoTime());
	}
	
	public Time(TimeUnit tu) {
		this(tu, tu.convert(System.currentTimeMillis(), TimeUnit.MICROSECONDS));
	}
	
	public Time(TimeUnit tu, long src) {
		this.tu = tu;
		this.src = src;
	}
	
	public TimeUnit getTimeUnit() {
		return tu;
	}
	
	public long getSrc() {
		return src;
	}
	
	public Time convert(TimeUnit to) {
		return new Time(to, to.convert(src, tu));
	}
	
	public Time elapsed() {
		return elapsed(new Time());
	}
	
	public Time elapsed(Time time) {
		long tg = time.convert(tu).src;
		long calc = tg - src;
		return new Time(tu, calc);
	}
	
	public String elapsedS(Time time) {
		return elapsed(time).toString();
	}
	
	public String elapsedS() {
		return elapsed().toString();
	}
	
	public Time elapsedF(Time time) {
		long tg = time.convert(tu).src;
		long calc = src - tg;
		return new Time(tu, Meth.negative(calc));
	}
	
	@Override
	public String toString() {
		return src + " " + Utility.capitalizeEnforce(tu.toString());
	}
}
