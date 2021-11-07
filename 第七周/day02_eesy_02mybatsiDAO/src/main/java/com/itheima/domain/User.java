package com.itheima.domain;

import java.io.Serializable;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
public class User implements Serializable {

   private int id;
   private String name;
   private String comment;

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", comment='" + comment + '\'' +
              '}';
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getComment() {

      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }
}
