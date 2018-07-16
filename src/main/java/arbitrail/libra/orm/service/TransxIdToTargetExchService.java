package arbitrail.libra.orm.service;

import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import arbitrail.libra.model.ExchStatus;
import arbitrail.libra.orm.dao.TransxIdToTargetExchDao;
import arbitrail.libra.orm.model.TransxIdToTargetExchEntity;

@Service
public class TransxIdToTargetExchService {

	@Autowired
	private TransxIdToTargetExchDao transxIdToTargetExchDao;
	
	@Transactional
	public void saveAll(Map<Integer, ExchStatus> transxIdToTargetExchMqp) {
		for (Entry<Integer, ExchStatus> entry : transxIdToTargetExchMqp.entrySet()) {
			Integer transxId = entry.getKey();
			ExchStatus exchStatus = entry.getValue();
			transxIdToTargetExchDao.persist(new TransxIdToTargetExchEntity(transxId.toString(), exchStatus.getExchangeName(), exchStatus.isWithdrawalComplete(), exchStatus.withdrawTime()));
		}
	}

	@Transactional(readOnly = true)
	public ConcurrentMap<Integer, ExchStatus> listAll() {
		List<TransxIdToTargetExchEntity> entityList = transxIdToTargetExchDao.findAll();
		
		ConcurrentMap<Integer, ExchStatus> pendingTransIdToToExchMap = new ConcurrentHashMap<>();
		for (TransxIdToTargetExchEntity entity : entityList) {
			pendingTransIdToToExchMap.put(Integer.parseInt(entity.getTransxId()), new ExchStatus(entity.getExchangeName(), entity.isWithdrawalComplete(), entity.withdrawTime()));
		}
		
		return pendingTransIdToToExchMap; 
	}
}
