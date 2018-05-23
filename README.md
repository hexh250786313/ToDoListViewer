# ToDoListViewer

### 这是一个用来显示备忘事项的JAVA程序  
![图片](http://pic.yupoo.com/sinaweibo4907754196_v/917796ab/6efd2750.png)  

## 基本功能
#### 读取
程序会读取你的ToDoList然后显示到界面，当你勾选上的复选框再点击“new”按钮，那个项目就会从你的ToDoList中消失，表示你已经完成了这个事件。  
#### 添加
当你勾选上蓝色高亮的“Build_Item”然后点击"new"按钮，就会弹出添加ToDo项目的对话框，输入完成后再点击"create"或者回车，文本框中的项目会添加到你的ToDoList中  
然后再在主程序中点击“new”，新添加的项目就会显示出来  
![图片](http://pic.yupoo.com/sinaweibo4907754196_v/65803aac/db28db57.png)  
## 注意的地方
#### 程序无法直接通过点击关闭
这是为了让程序不被错误点出，退出方法为同时勾选上两个红色高亮的“EXIT”复选框，再点击“new”，当然通过杀死进程来退出也是可以的  
#### 程序是一直置顶的
这是为了能让用户一直看到ToDoList  
#### 最小尺寸
我特意设置了一个最小尺寸，为了不妨碍用户的视线，直接拉伸边框到最小就行了，这时候点击"new"就能瞬间恢复成合适的大小（这时候要小心复选框被选上，或者我应该设置一个警告对话框）  
![图片](http://pic.yupoo.com/sinaweibo4907754196_v/f9b095dc/c892fedd.png)  
## 下载地址 and 怎么用？
百度网盘：链接: https://pan.baidu.com/s/1lY2Z_CGHc_XPbYKJXQoKeg 密码: mzix  
使用：先配置JAVA的运行环境吧，然后点击目录下的"run.bat"就能启动了  