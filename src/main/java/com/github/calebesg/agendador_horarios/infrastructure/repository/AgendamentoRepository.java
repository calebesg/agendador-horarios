package com.github.calebesg.agendador_horarios.infrastructure.repository;

import com.github.calebesg.agendador_horarios.infrastructure.entity.Agendamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    Agendamento findByServicoAndDataHoraAgendamentoBetween(
            String servico,
            LocalDateTime dataLHoraInicio,
            LocalDateTime dataHoraFim);

    @Transactional
    void deleteByDataHoraAgendamentoAndCliente(LocalDateTime dataHoraAgendamento, String cliente);

    // listar todos os agendamentos em um intervalo de tempo
    Agendamento findByDataHoraAgendamentoBetween(LocalDateTime dataHoraInicial, LocalDateTime dataHoraFinal);
}
