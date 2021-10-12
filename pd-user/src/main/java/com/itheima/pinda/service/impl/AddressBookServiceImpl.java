package com.itheima.pinda.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.pinda.entity.AddressBook;
import com.itheima.pinda.mapper.AddressBookMapper;
import com.itheima.pinda.service.IAddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements IAddressBookService {
}
