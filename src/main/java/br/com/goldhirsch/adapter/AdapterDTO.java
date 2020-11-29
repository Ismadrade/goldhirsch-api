package br.com.goldhirsch.adapter;

public interface AdapterDTO<Entity, Response, Request> {
    Response toResponse(Entity model);
    Entity ToEntity(Request request);
}
