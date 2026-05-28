// model/GlobalUserList.ets

import { User } from './User'

export class GlobalUserList {
  private static instance: GlobalUserList

  // 用户集合（内存）
  private users: User[] = []

  // 单例构造器
  private constructor() {
    // 初始化默认用户
    this.initDefaultUser()
  }

  public static getInstance(): GlobalUserList {
    if (!GlobalUserList.instance) {
      GlobalUserList.instance = new GlobalUserList()
    }
    return GlobalUserList.instance
  }

  // 初始化默认用户（用于测试）
  private initDefaultUser(): void {
    // 防止重复添加
    const exists = this.users.some(u => u.username === 'admin')
    if (!exists) {
      // 添加一个带默认头像的 admin 用户
      this.users.push(new User('admin', 'admin', $r('app.media.head')))
    }
  }


  // 添加用户（可选：提供默认头像）
  public addUser(user: User): void {
    const exists = this.users.some(u => u.username === user.username)
    if (exists) {
      throw new Error('用户名已存在')
    }

    if (!user.avatarUrl) {
      user.avatarUrl = $r('app.media.head')
    }

    this.users.push(user)
  }

  // 根据用户名查找用户
  public getUserByUsername(username: string): User | undefined {
    return this.users.find(user => user.username === username)
  }

  //更新用户
  public updateUser(user: User): void {
    const index = this.users.findIndex(u => u.username === user.username);
    if (index !== -1) {
      this.users[index] = user; // 替换旧数据
    } else {
      throw new Error("用户不存在");
    }
  }

  // 登录验证
  public login(username: string, password: string): boolean {
    const user = this.getUserByUsername(username)
    return !!user && user.password === password
  }

  // 获取所有用户（调试用）
  public getAllUsers(): User[] {
    return this.users
  }
}