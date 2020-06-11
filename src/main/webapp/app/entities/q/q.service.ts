import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IQ } from 'app/shared/model/q.model';

type EntityResponseType = HttpResponse<IQ>;
type EntityArrayResponseType = HttpResponse<IQ[]>;

@Injectable({ providedIn: 'root' })
export class QService {
  public resourceUrl = SERVER_API_URL + 'api/qs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/qs';

  constructor(protected http: HttpClient) {}

  create(q: IQ): Observable<EntityResponseType> {
    return this.http.post<IQ>(this.resourceUrl, q, { observe: 'response' });
  }

  update(q: IQ): Observable<EntityResponseType> {
    return this.http.put<IQ>(this.resourceUrl, q, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQ>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQ[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQ[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
