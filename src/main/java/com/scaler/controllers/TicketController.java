package com.scaler.controllers;

import com.scaler.dtos.IssueTicketRequestDTO;
import com.scaler.dtos.IssueTicketResponseDTO;
import com.scaler.dtos.ResponseStatus;
import com.scaler.models.Ticket;
import com.scaler.services.TicketService;

public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }
    public IssueTicketResponseDTO issueTicket(IssueTicketRequestDTO issueTicketRequestDTO){
        IssueTicketResponseDTO issueTicketResponseDTO = new IssueTicketResponseDTO();
        Ticket ticket = ticketService.issueTicket(issueTicketRequestDTO.getGateId(),
                issueTicketRequestDTO.getVehicleNumber(),
                issueTicketRequestDTO.getVehicleOwnerName(),
                issueTicketRequestDTO.getVehicleType());
        issueTicketResponseDTO.setTicket(ticket);
        issueTicketResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        return issueTicketResponseDTO;
    }
}
