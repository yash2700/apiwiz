package com.apiwiz.apiwiz.Service;

import com.apiwiz.apiwiz.Dtos.LoginDto;
import com.apiwiz.apiwiz.Dtos.SubscribeDto;
import com.apiwiz.apiwiz.Dtos.UserDto;
import com.apiwiz.apiwiz.Enums.Notify_Frequency;
import com.apiwiz.apiwiz.Model.Role;
import com.apiwiz.apiwiz.Model.Subscribe;
import com.apiwiz.apiwiz.Model.UserEntity;
import com.apiwiz.apiwiz.Repository.RoleRepository;
import com.apiwiz.apiwiz.Repository.SubscirbeRepository;
import com.apiwiz.apiwiz.Repository.UserRepository;
import com.apiwiz.apiwiz.ResponseDto.Res;
import com.apiwiz.apiwiz.Model.StockData;
import com.apiwiz.apiwiz.ResponseDto.Values;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


import static com.apiwiz.apiwiz.Enums.Notify_Frequency.Daily;


@Service
public class UserServiceImpl  implements  UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Autowired
    private SubscirbeRepository subscirbeRepository;

    @Autowired
    ObjectMapper mapper;
    @Autowired
    RestTemplate restTemplate;

    boolean isLoggedIn;

    ArrayList<Subscribe> list =new ArrayList<>();
    @Autowired
    private RoleRepository roleRepository;

    ArrayList<Subscribe> resultList=new ArrayList<>();


    @Override
    public UserEntity getByEmailId(String emailId) {
        return userRepository.findByEmail(emailId);
    }

    @Override
    public HashMap<String, HashMap<String, Object>> getBySymbol(String symbol,String start_date,String end_date) throws JsonProcessingException {
        String url="https://api.twelvedata.com/time_series?start_date="+start_date+"&end_date="+end_date+"&symbol="+symbol+"&interval=1day&apikey=ad13b3e49ee14786ac8d61a2d7a1eb4b";
        String response=restTemplate.getForObject(url,String.class);
        if(response.contains(("error"))){
            System.out.println("error");
            Map<String ,HashMap<String,Object>> error=new HashMap<>();
            HashMap<String,Object> errorRespsonse=new HashMap<>();
            errorRespsonse.put(("please check input"),new Res());
            error.put("error",errorRespsonse);
            return (HashMap<String, HashMap<String, Object>>) error;
        }
        ObjectMapper mapper1=new ObjectMapper();
        Res response1=mapper1.readValue(response, Res.class);
        Map<String,Object> hashMap=new LinkedHashMap<>();
        for(Values i:response1.getValues()){
            StockData resValue= StockData.builder().open(i.getopen())
                    .close(i.getClose())
                    .high(i.getHigh())
                    .close(i.getClose())
                    .volume(i.getVolume())                    .low(i.getLow())
                    .build();
            hashMap.put(i.getDatetime(),resValue);
        }
        HashMap<String,HashMap<String,Object>> res=new HashMap<>();
        res.put(symbol.toUpperCase(), (HashMap<String, Object>) hashMap);

        return res;
    }

    public String subscribe(SubscribeDto subscribeDto,String email){
        String currTime=new SimpleDateFormat("YYYY-MM-dd").format(new Date());
        Subscribe subscribe=Subscribe.builder()
                .date(currTime)
                .notifyFrequency(subscribeDto.getNotifyFrequency())
                .notifyTime(subscribeDto.getNotifyTime())
                .stockSymbol(subscribeDto.getStockSymbol())
                .email(email)
                .build();
        subscirbeRepository.save(subscribe);
        System.out.println(subscirbeRepository.findByDate(currTime));
        return "subscribed";
    }
    @Scheduled(cron = "0 31 18 * * *")
    public void getData(){
        String currDate=new SimpleDateFormat(("YYYY-MM-dd")).format(new Date());
        ArrayList<Subscribe> subscribes=subscirbeRepository.findByDate(currDate);
        subscribes.sort((a,b)->a.getNotifyTime().compareTo(b.getNotifyTime()));
        list=subscribes;
        resultList=new ArrayList<>();
        System.out.println(list);
    }
    @Scheduled(cron = "0 * * * * * ")
    public void sendMailHelper(){
        if(list.size()==0 || list==null){
            System.out.println("list is null");
            return;
        }
        String currTime=new SimpleDateFormat("HH:mm").format(new Date());
        while (list.get(0).getNotifyTime().equals(currTime)){
           list.get(0).setDate(resultDate(list.get(0).getDate(), list.get(0).getNotifyFrequency()));
           String symbol=list.get(0).getStockSymbol();
           String url="https://api.twelvedata.com/price?symbol="+symbol+"&apikey=ad13b3e49ee14786ac8d61a2d7a1eb4b";
           String data=restTemplate.getForObject(url, String.class);
           String res=symbol+"\n"
           +data;
           String subject="Alert price of "+symbol;
           sendMail(list.get(0).getEmail(),subject,res);
           resultList.add(list.get(0));
           list.remove(0);
       }
    }
    @Scheduled(cron = "0 59 23 * * *")
    public void addData(){
        subscirbeRepository.saveAll(resultList);
        resultList=null;
        list=null;
    }
    public String resultDate(String currDate, Notify_Frequency notifyFrequency){
        switch (notifyFrequency){
            case Daily:return LocalDate.parse(currDate).plusDays(1).toString();
            case Weekly:return  LocalDate.parse(currDate).plusWeeks(1).toString();
            case Biweekly:return LocalDate.parse(currDate).plusWeeks(2).toString();
            case Monthly:return LocalDate.parse(currDate).plusMonths(1).toString();
        }
        return null;
    }

    public void sendMail(String toEmail,String subject,String body){
        SimpleMailMessage message=new SimpleMailMessage();
//        message.setFrom("yaswanthreddyboggala@gmail.com");
        message.setFrom("yaswanthreddyboggaal@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        javaMailSender.send(message);
        System.out.println("message sent");
    }
}
