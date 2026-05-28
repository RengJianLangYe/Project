const base = {
    get() {
        return {
            url : "http://localhost:8080/springbootf3x3h/",
            name: "springbootf3x3h",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/front/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "NoteLock 协同笔记系统"
        } 
    }
}
export default base
