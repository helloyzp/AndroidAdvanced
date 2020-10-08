package com.example.javaadvanced.jvm.ex2.referencetype;

import java.lang.ref.WeakReference;

/**
 * @author King老师
 * 弱引用
 *
 * 用弱引用关联的对象，只能生存到下一次垃圾回收之前，GC发生时，不管内存够不够，都会被回收。
 */
public class TestWeakRef {
	public static class User{
		public int id = 0;
		public String name = "";
		public User(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + "]";
		}

	}

	public static void main(String[] args) {
		User user = new User(1,"King");
		WeakReference<User> userWeak = new WeakReference<User>(user);
		user = null;//干掉强引用，确保这个实例只有userWeak弱引用指向它
		System.out.println(userWeak.get());
		System.gc();//进行一次GC垃圾回收,千万不要写在业务代码中。
		System.out.println("After gc");
		System.out.println("userWeak.get()=" + userWeak.get());
	}
}
