package fr.crowdfunding.jhipster.service;

import fr.crowdfunding.jhipster.domain.Project;
import fr.crowdfunding.jhipster.repository.ProjectRepository;
import fr.crowdfunding.jhipster.service.dto.CategoryCardDTO;
import fr.crowdfunding.jhipster.service.dto.ProjectCardDTO;
import fr.crowdfunding.jhipster.service.dto.ProjectDTO;
import fr.crowdfunding.jhipster.service.mapper.ProjectMapper;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Project}.
 */
@Service
@Transactional
public class ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    /**
     * Save a project.
     *
     * @param projectDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectDTO save(ProjectDTO projectDTO) {
        log.debug("Request to save Project : {}", projectDTO);
        Project project = projectMapper.toEntity(projectDTO);
        project = projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    /**
     * Partially update a project.
     *
     * @param projectDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProjectDTO> partialUpdate(ProjectDTO projectDTO) {
        log.debug("Request to partially update Project : {}", projectDTO);

        return projectRepository
            .findById(projectDTO.getId())
            .map(
                existingProject -> {
                    projectMapper.partialUpdate(existingProject, projectDTO);

                    return existingProject;
                }
            )
            .map(projectRepository::save)
            .map(projectMapper::toDto);
    }

    /**
     * Get all the projects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectCardDTO> findAll(Pageable pageable) {
        List<ProjectCardDTO> project = choiceRequest(pageable);

        log.debug("Request to get all Projects");

        Page<ProjectCardDTO> page = new PageImpl(project, pageable, project.get(0).getNbRows().longValue());

        return page;
    }

    @Transactional(readOnly = true)
    public Page<ProjectCardDTO> findAllByUserId(Pageable pageable, Long id) {

        List<ProjectCardDTO> project = choiceRequest(pageable, id);

        Page<ProjectCardDTO> page = new PageImpl(project, pageable, project.get(0).getNbRows().longValue());

        return page;
    }

    public List<ProjectCardDTO> choiceRequest(Pageable pageable, Long... id) {

        String sort = pageable.getSort().toString();
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int startValue = pageNumber * pageSize;

        String[] sortFirstSplit = (sort.split(","));
        String[] sortFinalSplit = sortFirstSplit[0].split(": ");

        List<Object[]> result = new ArrayList<>();

        if(sortFinalSplit[0].equals("title")) {

            if(sortFinalSplit[1].equals("ASC") && id.length < 1) {
                result = projectRepository.findAllByTitleAsc(pageSize, startValue);
            } else if(sortFinalSplit[1].equals("DESC") && id.length < 1){
                result = projectRepository.findAllByTitleDesc(pageSize, startValue);
            } else if(sortFinalSplit[1].equals("ASC")){
                result = projectRepository.findAllByUserIdAndTitleAsc(pageSize, startValue, id[0]);
            } else if(sortFinalSplit[1].equals("DESC")){
                result = projectRepository.findAllByUserIdAndTitleDesc(pageSize, startValue, id[0]);
            }

        } else if(sortFinalSplit[0].equals("goal")) {

            if(sortFinalSplit[1].equals("ASC") && id.length < 1) {
                result = projectRepository.findAllByGoalAsc(pageSize, startValue);
            } else if(sortFinalSplit[1].equals("DESC") && id.length < 1){
                result = projectRepository.findAllByGoalDesc(pageSize, startValue);
            } else if(sortFinalSplit[1].equals("ASC")) {
                result = projectRepository.findAllByUserIdAndGoalAsc(pageSize, startValue, id[0]);
            } else if(sortFinalSplit[1].equals("DESC")){
                result = projectRepository.findAllByUserIdAndGoalDesc(pageSize, startValue, id[0]);
            }

        } else if(sortFinalSplit[0].equals("category.id")) {

            if(sortFinalSplit[1].equals("ASC") && id.length < 1) {
                result = projectRepository.findAllByCategoryIdAsc(pageSize, startValue);
            } else if(sortFinalSplit[1].equals("DESC") && id.length < 1){
                result = projectRepository.findAllByCategoryIdDesc(pageSize, startValue);
            } else if(sortFinalSplit[1].equals("ASC")) {
                result = projectRepository.findAllByUserIdAndCategoryIdAsc(pageSize, startValue, id[0]);
            } else if(sortFinalSplit[1].equals("DESC")){
                result = projectRepository.findAllByUserIdAndCategoryIdDesc(pageSize, startValue, id[0]);
            }

        } else {
            if(id.length >= 1) {
                result = projectRepository.findAllByUserIdAndTitleAsc(pageSize, startValue, id[0]);
            } else {
                result = projectRepository.find(pageSize, startValue);
            }
        }

        return convertListObjectInProjectCardDTO(result);
    }

    public List<ProjectCardDTO> convertListObjectInProjectCardDTO(List<Object[]> result) {
        List<ProjectCardDTO> project = new ArrayList<>();

        for (Object[] res : result) {
            ProjectCardDTO p = new ProjectCardDTO();
            CategoryCardDTO c = new CategoryCardDTO();

            p.setParticipants((BigInteger) res[0]);
            p.setAmount((Double) res[1]);
            p.setTitle((String) res[2]);
            p.setId((BigInteger) res[3]);
            p.setGoal((Double) res[4]);
            p.setHeadline((String) res[5]);
            p.setDuration((Integer)res[6]);
            p.setCreatedAt(((Timestamp) res[7]).toInstant());
            p.setNbRows((BigInteger)res[10]);

            c.setId((BigInteger) res[8]);
            c.setName((String) res[9]);
            p.setCategory(c);
            project.add(p);
        }
        return project;
    }

    /**
     *  Get all the projects where BalanceTransfer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectDTO> findAllWhereBalanceTransferIsNull() {
        log.debug("Request to get all projects where BalanceTransfer is null");
        return StreamSupport
            .stream(projectRepository.findAll().spliterator(), false)
            .filter(project -> project.getBalanceTransfer() == null)
            .map(projectMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one project by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectDTO> findOne(Long id) {
        log.debug("Request to get Project : {}", id);
        return projectRepository.findById(id).map(projectMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<Project> getProject(Long id) {
        return projectRepository.findById(id);
    }

    /**
     * Delete the project by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.deleteById(id);
    }
}
