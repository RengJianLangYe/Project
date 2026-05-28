// model/GlobalState.ets

import { User } from './User'

export class GlobalState {
  private static instance: GlobalState
  private _currentUser: User | null = null  //当前登录用户
  private _isLoggedIn: boolean = false // 登录状态

  // 私有构造器（单例模式）
  private constructor() {}

  // 获取单例实例
  public static getInstance(): GlobalState {
    if (!GlobalState.instance) {
      GlobalState.instance = new GlobalState()
    }
    return GlobalState.instance
  }

  // 设置当前用户
  public setCurrentUser(user: User): void {
    this._currentUser = user
  }

  // 获取当前用户
  public getCurrentUser(): User | null {
    return this._currentUser
  }


  // 获取登录状态
  public isLoggedIn(): boolean {
    return this._isLoggedIn
  }

  //清除当前用户，重新设置登录状态
  public clearCurrentUser():void{
    this._currentUser=new User('游客', '', $r('app.media.head'))
    this._isLoggedIn = false
  }

  // 设置登录状态
  public setLoginStatus(status: boolean): void {
    this._isLoggedIn = status
  }
}