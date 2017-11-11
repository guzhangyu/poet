package com.poet;

/**
 * Created by guzy on 17/10/31.
 */
public class StockProblems {

    public static void main(String[] args) {
        int companyId = 1063;

        int dbNoRecord = (companyId % 80);
        int dbNoOrder = (companyId % 80);
        int dbNoTrade = (companyId % 50);
        int dbNoStock = (companyId % 80);

        int pageStart = 0;
        int pageSize = 200;

        //一个order存在多个record
        String sql = "select order_id from stock_order_record_" + dbNoRecord + " where company_id=" + companyId + " and enable_status=1 group by order_id having count(*)>1 limit " + pageStart + "," + pageSize;
        printMsg("一个order存在多个record", sql);

        //order不存在,record存在
        sql = "select distinct a.sid from stock_order_record_" + dbNoRecord + " a where company_id = " + companyId + " and enable_status = 1 and not exists (select 1 from order_" + dbNoOrder + " b where b.company_id=" + companyId + " and b.enable_status = 1 and a.order_id = b.id) order by a.sid asc limit " + pageStart + "," + pageSize;
        printMsg("order不存在,record存在", sql);

        //order存在,record不存在
        sql = "SELECT a.sid,a.id order_id from order_" + dbNoOrder + " a inner join trade_" + dbNoTrade + " b on a.sid=b.sid and b.is_cancel=0 where a.company_id=" + companyId + " and a.is_virtual=0 and a.enable_status=1 and a.stock_status not in ('UNALLOCATED') and (a.sys_status in ('WAIT_AUDIT','FINISHED_AUDIT') or (a.sys_status='WAIT_BUYER_PAY' and a.sub_stock=0)) and not exists (select 1 from stock_order_record_" + dbNoRecord + " t where t.company_id=" + companyId + " and t.enable_status=1 and a.id=t.order_id) limit " + pageStart + "," + pageSize;
        printMsg("order存在，record不存在", sql);

        //order已完结,record未释放
        sql = "select a.sid,a.order_id from stock_order_record_" + dbNoRecord + " a inner join order_" + dbNoOrder + " b on a.order_id = b.id where a.company_id=" + companyId + " and a.enable_status=1 and b.enable_status=1 and b.sys_status in ('SELLER_SEND_GOODS', 'FINISHED', 'CLOSED') limit " + pageStart + "," + pageSize;
        printMsg("order已完结，record未释放", sql);

        //order不正常,record正常
        sql = "select a.sid,a.order_id from stock_order_record_" + dbNoRecord + " a inner join order_" + dbNoOrder + " b on a.order_id=b.id where a.company_id=" + companyId + " and a.enable_status=1 and a.difference_value=0 and b.enable_status=1 and (b.sys_status in ('WAIT_AUDIT','FINISHED_AUDIT') or (b.sys_status='WAIT_BUYER_PAY' and b.sub_stock=0)) and b.is_virtual=0 and b.stock_status != 'NORMAL' AND (b.type!=2 OR b.combine_id!=0) limit " + pageStart + "," + pageSize;
        printMsg("order不正常,record正常", sql);

        //order正常，record不正常
        sql = "select a.sid,a.order_id from stock_order_record_" + dbNoRecord + " a inner join order_" + dbNoOrder + " b on a.order_id=b.id where a.company_id=" + companyId + " and a.enable_status=1 and a.difference_value>0 and b.enable_status=1 and (b.sys_status in ('WAIT_AUDIT','FINISHED_AUDIT') or (b.sys_status='WAIT_BUYER_PAY' and b.sub_stock=0)) and b.is_virtual=0 and b.stock_status = 'NORMAL' AND (b.type!=2 OR b.combine_id!=0) limit " + pageStart + "," + pageSize;
        printMsg("order正常，record不正常", sql);

        //record的锁定数与stock不一致
        sql = "select distinct a.sys_item_id from stock_" + dbNoStock + " a inner join (select warehouse_id,sys_item_id,sys_sku_id,sum(num) apply_num,sum(stock_num) stock_num from stock_order_record_" + dbNoRecord + " where company_id=" + companyId + " and enable_status=1 group by warehouse_id,sys_item_id,sys_sku_id) b on a.ware_house_id=b.warehouse_id and a.sys_item_id=b.sys_item_id and a.sys_sku_id=b.sys_sku_id and a.company_id=" + companyId + " and a.enable_status=1 and a.lock_stock!=b.apply_num limit " + pageStart + "," + pageSize;
        printMsg("record的锁定数与stock不一致", sql);

        //record的分配数与stock总库存不一致(1、stock总库存小于record的总库存为异常 2、stock总库存大于等于record总库存，但record存在不足的的记录)
        sql = "select a.*,b.apply_num,b.stock_num,b.difference_num from (select company_id,ware_house_id,sys_item_id,sys_sku_id,(lock_stock+available_in_stock) sum_stock from stock_" + dbNoStock + " where company_id=" + companyId + " AND enable_status=1) a inner join (select warehouse_id,sys_item_id,sys_sku_id,sum(num) apply_num,sum(stock_num) stock_num,sum(difference_value) difference_num from stock_order_record_" + dbNoRecord + " where company_id=" + companyId + " and enable_status=1 group by warehouse_id,sys_item_id,sys_sku_id) b on a.ware_house_id=b.warehouse_id and a.sys_item_id=b.sys_item_id and a.sys_sku_id=b.sys_sku_id and ((a.sum_stock>0 and a.sum_stock>=b.stock_num and a.sum_stock>b.apply_num and b.difference_num>0)  ) limit " + pageStart + "," + pageSize;
        printMsg("record分配数与stock总库存不一致 stock大于", sql);

        sql = "select a.*,b.apply_num,b.stock_num,b.difference_num from (select company_id,ware_house_id,sys_item_id,sys_sku_id,(lock_stock+available_in_stock) sum_stock from stock_" + dbNoStock + " where company_id=" + companyId + " AND enable_status=1) a inner join (select warehouse_id,sys_item_id,sys_sku_id,sum(num) apply_num,sum(stock_num) stock_num,sum(difference_value) difference_num from stock_order_record_" + dbNoRecord + " where company_id=" + companyId + " and enable_status=1 group by warehouse_id,sys_item_id,sys_sku_id) b on a.ware_house_id=b.warehouse_id and a.sys_item_id=b.sys_item_id and a.sys_sku_id=b.sys_sku_id and ((a.sum_stock <b.stock_num and b.stock_num>0) ) limit " + pageStart + "," + pageSize;
        printMsg("record分配数与stock总库存不一致 stock小于", sql);
    }

    private static void  printMsg(String msg,String sql){
        System.out.println("-- "+msg);
        System.out.println(sql);
    }
}
