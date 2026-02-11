package com.github.calebesg.agendador_horarios.services;

import com.github.calebesg.agendador_horarios.infrastructure.entity.Agendamento;
import com.github.calebesg.agendador_horarios.infrastructure.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AgendamentoService {
    private final AgendamentoRepository agendamentoRepository;

    public Agendamento salvarAgendamento(Agendamento agendamento) {
        LocalDateTime horaAgendamento = agendamento.getDataHoraAgendamento();
        LocalDateTime horaTermino = horaAgendamento.plusHours(1);

        Agendamento agendamentoEncontrado = agendamentoRepository.findByServicoAndDataHoraAgendamentoBetween(
                agendamento.getServico(),
                horaAgendamento, horaTermino);

        if (Objects.nonNull(agendamentoEncontrado)) {
            throw new RuntimeException("Horário já está preenchido!");
        }

        return agendamentoRepository.save(agendamento);
    }

    public void deletarAgendamento (LocalDateTime dataHoraAgendamento, String cliente) {
        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
    }

    public List<Agendamento> buscarAgendamentoDoDia(LocalDate data) {
        LocalDateTime primeiraHoraDoDia = data.atStartOfDay();
        LocalDateTime ultimaHoraDoDia = data.atTime(23, 59);

        return agendamentoRepository.findByDataHoraAgendamentoBetween(primeiraHoraDoDia, ultimaHoraDoDia);
    }

    public Agendamento alterarAgendamento (Agendamento agendamento, String cliente, LocalDateTime dataHoraAgendamento) {
        Agendamento agenda = agendamentoRepository.findByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);

        if (Objects.isNull(agenda)) {
            throw new RuntimeException("Nem um serviço agendado neste horário!");
        }

        agendamento.setId(agenda.getId());
        return agendamentoRepository.save(agendamento);
    }
}
