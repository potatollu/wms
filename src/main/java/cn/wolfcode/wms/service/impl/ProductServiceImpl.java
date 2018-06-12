package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.domain.Product;
import cn.wolfcode.wms.mapper.BrandMapper;
import cn.wolfcode.wms.mapper.ProductMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IProductService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService{
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Override
    public void saveOrUpdate(Product entity) {
        Brand brand = brandMapper.selectByPrimaryKey(entity.getBrandId());
        entity.setBrandName(brand.getName());
        if (entity.getId() != null) {
            productMapper.updateByPrimaryKey(entity);
        }else {
            productMapper.insert(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            productMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public Product get(Long id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Product> list() {
        return productMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer rows = productMapper.query4Count(qo);
        System.out.println(rows);
        if (rows == 0){
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = productMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,data);
    }
}
