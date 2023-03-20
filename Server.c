#include<stdio.h>
#include<stdlib.h>
#include<errno.h>
#include<string.h>
#include<sys/types.h>
#include<netinet/in.h>
#include<sys/socket.h>
#include<sys/wait.h>
#include<unistd.h>
#include<arpa/inet.h>

#define PORT 1500//端口号 
#define BACKLOG 5/*最大监听数*/ 
#define MAX_MSG 1024//接收到的数据最大程度 

void communicate(void);
void send_loop(void);
void recv_loop(void);

int sockfd,client_sock_fd;/*socket句柄和建立连接后的句柄*/
int main(){

    struct sockaddr_in my_addr;/*本方地址信息结构体，下面有具体的属性赋值*/
    struct sockaddr_in client_addr;/*对方地址信息*/
    int sin_size;

    //建立socket 
    sockfd=socket(AF_INET,SOCK_STREAM,0);//AF_INET: ipv4,  SOCK_STREAM: tcp
    if(sockfd==-1){
        printf("socket failed:%d",errno);
        return -1;
    }

    //绑定地址结构体和socket
    my_addr.sin_family=AF_INET;/*该属性表示接收本机或其他机器传输*/
    my_addr.sin_port=htons(PORT);//端口号 hton将主机字节顺序转换为网络字节顺序
    my_addr.sin_addr.s_addr=htonl(INADDR_ANY);/*IP，括号内容表示本机IP*/
    bzero(&(my_addr.sin_zero),8);/*将其他属性置0*/
    if(bind(sockfd,(struct sockaddr*)&my_addr,sizeof(struct sockaddr)) < 0){
        printf("bind error");
        return -1;
    }
    
    //开启监听 ，第二个参数是最大监听数
    listen(sockfd,BACKLOG); 

    //收消息，处理
    while(1){
        sin_size=sizeof(struct sockaddr_in);
        client_sock_fd=accept(sockfd,(struct sockaddr*)&client_addr,&sin_size);//在这里阻塞知道接收到消息，参数分别是socket句柄，接收到的地址信息以及大小 
        if(client_sock_fd==-1){
            printf("receive failed\n");
        } 
        else{
            printf("receive success\n");
            communicate();
            close(client_sock_fd);
            close(sockfd);
            break;
            // send(new_fd,"Hello World!",12,0);//发送内容，参数分别是连接句柄，内容，大小，其他信息（设为0即可） 
            }
    }

    return 0;
} 


void communicate(void)
{
    int fd_send = fork();
    if(fd_send == 0){
        send_loop();
    }

    int fd_recv = fork();
    if(fd_recv == 0){
        recv_loop();
    }

    int status;
    wait(&status);
}

void send_loop(void){
    char msg_buf_out[MAX_MSG];
    while(1){
        scanf("%s",msg_buf_out);
        if(strcmp(msg_buf_out,"exit") == 0) exit(0);

        int send_count = send(client_sock_fd,msg_buf_out,strlen(msg_buf_out),0);
        if(send_count == -1)
        {
            printf("server send fail\n");
        }
        else{
            // printf("server send success\nsend byte:%d\n",send_count);
        }

    }
}


void recv_loop(void){
    while(1){
        char msg_buf_in[MAX_MSG] = {};
        int recv_count = recv(client_sock_fd,msg_buf_in,MAX_MSG,0);
        if(recv_count < 0){
            printf("recv error\n");
            continue;
        }
        printf("Client: %s\n",msg_buf_in);
        // printf("recv count: %d\n",recv_count);
        fflush(stdout);
    }
}