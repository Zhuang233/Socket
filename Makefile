SRCS := $(shell find ./ -name "*.c")# 扫描当前目录所有.c文件
TARGETS = $(addprefix $(BUILD_DIR)/, $(basename $(SRCS))) # basename去前缀，addprefix加前缀，得到目标文件及其路径
CC = gcc
CFLAGS = -g -o
BUILD_DIR = build

all: $(BUILD_DIR) $(TARGETS) test


.PHONY : clean
clean:
	@rm -rf $(BUILD_DIR)

.PHONY : test
test:
	$(BUILD_DIR)/sh1

$(BUILD_DIR):
	@mkdir $(BUILD_DIR)

$(BUILD_DIR)/% : %.c
	$(CC) $(CFLAGS) $@ $<