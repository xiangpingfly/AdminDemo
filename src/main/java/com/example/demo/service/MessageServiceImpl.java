package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Message;
import com.example.demo.repository.MessageRepository;

//@CacheConfig(cacheNames = "message")
@Service
public class MessageServiceImpl {

    @Autowired
    private MessageRepository messageRepository;

    /**
     * 分页查询所有;
     * @return
     */
    //@Cacheable(value = "message",key = "getMethodName()+'['+#a0.pageNumber+']'+'['+#a0.pageSize+']'+'['+#a0.sort+']'")
    public Page<Message> findAll(Pageable pageable){
        Page<Message> page;
        try {
            page = messageRepository.findAll(pageable);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询数据库字段时出现异常");
        }

        return  page;
    }

    /**
     * 通过id查询单个;
     * @param id
     * @return
     */
    //@Cacheable(value = "message",key = "getMethodName()+'['+#a0+']'")
    public Message findById(Long id){
        Optional<Message> temp = messageRepository.findById(id);
        return temp.get();
    }

    /**
     * 通过Example例子查询单个;
     * @param example
     * @return
     */
    public Message findOne(Example<Message> example){
        Optional<Message> temp = messageRepository.findOne(example);
        return temp.get();
    }

    /**
     * 通过Example查询所有;
     * @param example
     * @param pageable
     * @return
     */
    public Page<Message> findAllByExample(Example<Message> example, Pageable pageable){
        Page<Message> page = messageRepository.findAll(example, pageable);
        return page;
    }

    /**
     * 持久化单条数据;
     * @param object
     */
    public void save(Message object){
        if (null == object){
            throw new RuntimeException("传入的参数不能为空");
        }
        try {
            messageRepository.save(object);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("更新数据库字段时出现异常");
        }
    }

    /**
     * 通过id查询所有;
     * @param list
     * @return
     */
    public List<Message> findAllById(List<Long> list){
        List<Message> allById = messageRepository.findAllById(list);
        return allById;
    }

    /**
     * 持久化并返回id;
     * @param object
     */
    //@CacheEvict(value = "message" )
    public long saveAndFlush(Message object){
        if (null == object){
            throw new RuntimeException("传入的参数不能为空");
        }
        Long id=null;
        try {
            Message temp = messageRepository.saveAndFlush(object);
            id = temp.getId();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("更新数据库字段时出现异常");
        }
        return id;
    }

    /**
     * 持久化所有;
     * @param list
     */
    //@CacheEvict(value = "message" )
    public void saveAll(List<Message> list){
        if (null == list || 0 == list.size()){
            throw new RuntimeException("传入的参数不能为空");
        }
        try {
            messageRepository.saveAll(list);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("持久化数据库字段时出现异常");
        }
    }

    /**
     * 通过Id删除单条记录;
     * @param id
     */
    //@CacheEvict(value = "message" )
    public void delete(Long id){
        if (null == id){
            throw new RuntimeException("传入的参数不能为空");
        }
        try{
            messageRepository.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("删除数据库字段时出现异常");
        }
    }

    /**
     * 删除所有;
     * @param list
     */
    //@CacheEvict(value = "message" )
    public void deleteAll(List<Message> list){
        if (null == list || 0 == list.size()){
            throw new RuntimeException("传入的参数不能为空");
        }
        try{
            messageRepository.deleteAll(list);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("删除数据库字段时出现异常");
        }
    }

    /**
     * 统计总数;
     * @return
     */
    public Long count(){
        long count = messageRepository.count();
        return count;
    }

    /**
     * 通过样子统计;
     * @param example
     * @return
     */
    public Long countByExample(Example<Message> example){
        long count = messageRepository.count(example);
        return count;
    }
}
