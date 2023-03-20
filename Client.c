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

 
#define DEST_PORT 1500//目标地址端口号 
#define DEST_IP "127.0.0.1"/*目标地址IP，这里设为本机*/ 
#define MAX_MSG 1024//接收到的数据最大程度 

int sockfd,server_sock_fd;/*cocket句柄和接受到连接后的句柄 */


void communicate(void);
void send_loop(void);
void recv_loop(void);

int main(){
    struct sockaddr_in server_addr;/*目标地址信息*/

    //建立socket
    sockfd=socket(AF_INET,SOCK_STREAM,0);
    if(sockfd==-1){
        printf("socket failed:%d\n",errno);
    }


    //建立连接
    server_addr.sin_family=AF_INET;
    server_addr.sin_port=htons(DEST_PORT);
    server_addr.sin_addr.s_addr=inet_addr(DEST_IP);
    bzero(&(server_addr.sin_zero),8);

    if(connect(sockfd,(struct sockaddr*)&server_addr,sizeof(struct sockaddr))==-1){//连接方法，传入句柄，目标地址和大小 
        printf("connect failed:%d\n",errno);//失败时可以打印errno 
    } else{
        printf("connect success\n");
        communicate();
    }
    close(sockfd);//关闭socket 
    return 0;
} 

void communicate(void)
{

    // int fd_send = fork();
    // if(fd_send == 0){
    //     send_loop();
    // }

    int fd_recv = fork();
    if(fd_recv == 0){
        recv_loop();
    }

    int status;
    wait(&status);
}

// void send_loop(void){
//     char msg_buf_out[MAX_MSG];
//     while(1){
//         scanf("%s",msg_buf_out);
//         if(strcmp(msg_buf_out,"exit") == 0) exit(0);
//         send(server_sock_fd,msg_buf_out,strlen(msg_buf_out),0);
//     }
// }

void recv_loop(void){
    while(1){
        char msg_buf_in[MAX_MSG] = {};
        int recv_count = recv(sockfd,msg_buf_in,MAX_MSG,0);
        if(recv_count < 0){
            printf("recv error\n");
            continue;
        }
        printf("%s\n",msg_buf_in);
        printf("recv count: %d\n",recv_count);
        fflush(stdout);
    }
}