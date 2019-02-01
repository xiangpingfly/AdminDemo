package com.example.demo.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Essay;
import com.example.demo.entity.Story;
import com.example.demo.entity.Topic;
import com.example.demo.entity.User;
import com.example.demo.service.EssayServiceImpl;
import com.example.demo.service.StoryServiceImpl;
import com.example.demo.service.TopicServiceImpl;
import com.example.demo.service.UserServiceImpl;

/**
 * 用于处理用户点击某个作者后显示此作者的稿件主页的方法;
 * @author kingguanzhang
 *
 */
@Controller
public class AuthorController {

    @Autowired
    private StoryServiceImpl storyService;
    @Autowired
    private EssayServiceImpl essayService;
    @Autowired
    private TopicServiceImpl topicService;
    @Autowired
    private UserServiceImpl userService;

    /**
     * 查看用户撰写的story页面;
     * @param model
     * @param pn
     * @param authorId
     * @return
     */
    @RequestMapping("/author/story")
    public String toUserStoryPage(Model model,
                                  @RequestParam(value = "pn",defaultValue = "1")Integer pn,
                                  @RequestParam(value = "authorId",defaultValue = "1")long authorId){

        /**
         * 取出用户撰写的story,分页并排序;注意pn因为pageRequest默认是从0开始的,所有要处理一下
         */
        Pageable pageable = new PageRequest(pn-1,10,new Sort(Sort.Direction.DESC,"creatTime"));
        Story story = new Story();
        /**
         * 判断作者参数是否有误;
         */
        if (0 >= authorId){
            model.addAttribute("errorMsg","传入参数有误!");
            return "error/promptMessage";
        }
        User user = new User();
        user.setId(authorId);
        story.setAuthor(user);

        User author = userService.findById(authorId);
        model.addAttribute("author",author);
        story.setStatus(1);

        //注意这里,story里面有几个属性是long类型,默认值是0而不是null,erexample里传过去不是null时就会开启匹配,所以需要设置忽略掉id属性;
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("id").withIgnorePaths("collectNumber").withIgnorePaths("commentNumber");
        Example<Story> example = Example.of(story,exampleMatcher);
        Page<Story> storyPage = storyService.findAllByExample(example, pageable);
        model.addAttribute("storyPage",storyPage);
        return "portal/otherUserStory";
    }

    /**
     * 查看用户撰写的essay页面;
     * @param model
     * @param pn
     * @param authorId
     * @return
     */
    @RequestMapping("/author/essay")
    public String toUserEssayPage(Model model,
                                  @RequestParam(value = "pn",defaultValue = "1")Integer pn,
                                  @RequestParam(value = "authorId",defaultValue = "0")long authorId){

        /**
         * 取出用户撰写的story,分页并排序;注意pn因为pageRequest默认是从0开始的,所有要处理一下,还有页面是9宫格板式,只查出9个即可
         */
        Pageable pageable = new PageRequest(pn-1,9,new Sort(Sort.Direction.DESC,"creatTime"));
        Essay essay = new Essay();
        if (0 >= authorId){
            model.addAttribute("errorMsg","传入参数有误!");
            return "error/promptMessage";
        }
        User user = new User();
        user.setId(authorId);
        essay.setAuthor(user);
        User author = userService.findById(authorId);
        model.addAttribute("author",author);
        essay.setStatus(1);

        //注意这里,story里面有几个属性是long类型,默认值是0而不是null,erexample里传过去不是null时就会开启匹配,所以需要设置忽略掉id属性;
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("id").withIgnorePaths("collectNumber");
        Example<Essay> example = Example.of(essay,exampleMatcher);
        Page<Essay> essayPage = essayService.findAllByExample(example, pageable);
        model.addAttribute("essayPage",essayPage);

        return "portal/otherUserEssay";
    }

    /**
     * 查看用户撰写的topic页面
     * @param model
     * @param pn
     * @param authorId
     * @return
     */
    @RequestMapping("/author/topic")
    public String toUserTopicPage(Model model,
                                  @RequestParam(value = "pn",defaultValue = "1")Integer pn,
                                  @RequestParam(value = "authorId",defaultValue = "0")long authorId){

        /**
         * 设置分页和排序条件;注意pn因为pageRequest默认是从0开始的,所有要处理一下
         */
        Pageable pageable = new PageRequest(pn-1,9,new Sort(Sort.Direction.DESC,"creatTime"));
        Topic topic = new Topic();


        /**
         * 判断参数是否有误;
         */
        if (0 >= authorId){
            model.addAttribute("errorMsg","传入参数有误!");
            return "error/promptMessage";
        }
        User user = new User();
        user.setId(authorId);
        topic.setAuthor(user);
        User author = userService.findById(authorId);
        model.addAttribute("author",author);
        topic.setStatus(1);

        //TODO : 这里可以更新下匹配方法和pageRequest方法;
        //注意这里,story里面有几个属性是long类型,默认值是0而不是null,erexample里传过去不是null时就会开启匹配,所以需要设置忽略掉id属性;
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("id").withIgnorePaths("collectNumber").withIgnorePaths("commentNumber");
        Example<Topic> example = Example.of(topic,exampleMatcher);
        Page<Topic> topicPage = topicService.findAllByExample(example, pageable);
        model.addAttribute("topicPage",topicPage);

        return "portal/otherUserTopic";
    }


}
